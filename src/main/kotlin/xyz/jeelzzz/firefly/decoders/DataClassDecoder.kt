package xyz.jeelzzz.firefly.decoders

import xyz.jeelzzz.firefly.ConfigNode
import xyz.jeelzzz.firefly.FireflyLoader
import xyz.jeelzzz.firefly.FireflyLoaderOptions
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class DataClassDecoder(val loader: FireflyLoader) {

    fun <T : Any> decode(kClass: KClass<T>, node: ConfigNode, options: FireflyLoaderOptions): T {
        if (!kClass.isData) throw IllegalArgumentException("Expected type argument to be a data class")
        val properties = kClass.memberProperties
        val args = mutableMapOf<String, Any?>()
//            TODO optional decorator for remapping
        for (property in properties) {
            val propertyKClass = property.returnType.classifier as? KClass<*>
            val decodedPropertyValue: Any?
            // Property is subclass
            if (propertyKClass != null && propertyKClass.isData) {
                decodedPropertyValue = decode(propertyKClass, node.createChild(property.name), options)
            }
            // Property is normal value
            else {
                val decoder = options.decoders[property.returnType]
                    ?: throw IllegalArgumentException("Property ${property.name} on ${kClass.qualifiedName} had type ${propertyKClass?.qualifiedName} but no decoder was found")
                decodedPropertyValue = decoder.decode(node.createChild(property.name))
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