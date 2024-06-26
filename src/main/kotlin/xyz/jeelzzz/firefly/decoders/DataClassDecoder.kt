package xyz.jeelzzz.firefly.decoders

import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.DecodeContext
import xyz.jeelzzz.firefly.FireflyLoader
import xyz.jeelzzz.firefly.ValueDecoder
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class DataClassDecoder(val loader: FireflyLoader) {

    fun <T : Any> decode(kClass: KClass<T>, parentNode: ConfigNode, ctx: DecodeContext): T {
        if (!kClass.isData) throw IllegalArgumentException("Expected type argument to be a data class")
        val properties = kClass.memberProperties
        val args = mutableMapOf<String, Any?>()
//            TODO optional decorator for remapping
        for (property in properties) {
            val propertyKClass = property.returnType.classifier as? KClass<*>
            val decodedPropertyValue: Any?

            val node = parentNode.createChild(property.name)

            // Property is subclass
            if (propertyKClass != null && propertyKClass.isData) {
                decodedPropertyValue = decode(propertyKClass, node, ctx)
            }

            // Property is list
            else if (propertyKClass == List::class) {
                val arguments = property.returnType.arguments
                if (arguments.isEmpty())
                    throw IllegalArgumentException("Property ${property.name} had no generic list type")
                val type = arguments.first().type ?:
                    throw IllegalArgumentException("Property ${property.name} had no generic list type")
                val elementKClass =  (type.classifier as? KClass<*>)
                if (elementKClass != null && elementKClass.isData) {
                    decodedPropertyValue = ListDecoder().decodeDataClass(this, elementKClass, node, ctx)
                } else {
                    val decoder = ctx.options.decoders[type]
                        ?: throw IllegalArgumentException("Property ${property.name} on ${kClass.qualifiedName} had generic list type $type but no decoder was found")
                    decodedPropertyValue = ListDecoder().decode(decoder as ValueDecoder<Any>, node, ctx)
                }
            }

            // Property is normal value
            else {
                val decoder = ctx.options.decoders[property.returnType]
                    ?: throw IllegalArgumentException("Property ${property.name} on ${kClass.qualifiedName} had type ${propertyKClass?.qualifiedName} but no decoder was found")
                decodedPropertyValue = decoder.decode(node, ctx)
            }

            args[property.name] = decodedPropertyValue
        }

        val primaryConstructor = kClass.primaryConstructor
        if (primaryConstructor != null) {
            return primaryConstructor.callBy(args.mapKeys { entry -> primaryConstructor.parameters.first { it.name == entry.key } })
        } else {
            throw IllegalArgumentException("No primary constructor found for class ${kClass.qualifiedName}")
        }
    }
}