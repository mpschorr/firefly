package xyz.jeelzzz.firefly.decoders

import xyz.jeelzzz.firefly.*
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class DataClassDecoder(val loader: FireflyLoader) {

    fun <T : Any> decode(kClass: KClass<T>, parentNode: ConfigNode, ctx: DecodeContext): T {
        if (!parentNode.configuration.isConfigurationSection(parentNode.bukkitKey))
            throw ValueDecodeException("Expected value \"${parentNode.decodeKey}\" to be a configuration section")
        if (!kClass.isData) throw IllegalArgumentException("Expected type argument to be a data class")
        val properties = kClass.memberProperties
        val args = mutableMapOf<String, Any?>()
//        TODO optional decorator for remapping
//        TODO nullability
        for (property in properties) {
            val propertyKClass = property.returnType.classifier as? KClass<*>
            val decodedPropertyValue: Any?

            val node = parentNode.createChild(property.name)

//            if (property.returnType.isMarkedNullable && property.)

            if (!node.exists()) {
                if (property.returnType.isMarkedNullable) {
                    args[property.name] = null
                    continue
                } else {
                    throw ValueDecodeException("Property ${node.decodeKey} was required but was not found")
                }
            }

            // Property is sub data class
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

                val elementKClass = (type.classifier as? KClass<*>)
                // List element is data class
                if (elementKClass != null && elementKClass.isData) {
                    decodedPropertyValue = ListDecoder().decodeDataClass(this, elementKClass, node, ctx)
                }
                // List element is scalar value
                else {
                    val decoder = ctx.options.decoders[type]
                        ?: throw IllegalArgumentException("Property ${property.name} on ${kClass.qualifiedName} had generic list type $type but no decoder was found")
                    decodedPropertyValue = ListDecoder().decode(decoder as ValueDecoder<Any>, node, ctx)
                }
            }

            // Property is normal value
            else {
                val decoder = ctx.options.decoders[property.returnType]
                    ?: throw ValueDecodeException("Property ${property.name} on ${kClass.qualifiedName} had type ${propertyKClass?.qualifiedName} but no decoder was found")
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