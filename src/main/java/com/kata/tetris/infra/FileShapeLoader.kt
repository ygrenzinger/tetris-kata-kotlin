package com.kata.tetris.infra

import com.google.gson.*
import com.kata.tetris.domain.tetromino.Shape
import com.kata.tetris.domain.tetromino.ShapeForm
import com.kata.tetris.domain.tetromino.ShapeLoader
import com.kata.tetris.domain.tetromino.ShapeType
import java.io.IOException
import java.lang.reflect.Type
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer




private class ShapeFormSerializer : JsonSerializer<ShapeForm> {
    val gson = Gson()
    override fun serialize(src: ShapeForm, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(gson.toJson(src.blocks))
    }
}

private class ShapeFormDeserializer : JsonDeserializer<ShapeForm> {
    val gson = Gson()
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ShapeForm {
        return ShapeForm(gson.fromJson(json, Array<BooleanArray>::class.java))
    }
}

class FileShapeLoader : ShapeLoader {
    private val gson: Gson

    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(ShapeForm::class.java, ShapeFormSerializer())
        gsonBuilder.registerTypeAdapter(ShapeForm::class.java, ShapeFormDeserializer())
        gson = gsonBuilder.create()
    }

    override fun loadShape(shapeType: ShapeType): Shape {
        try {
            val resource = Shape::class.java.getResource(shapeType.name + ".json")
            val path = Paths.get(resource.toURI())
            return loadShape(path)
        } catch (e: URISyntaxException) {
            throw RuntimeException("Impossible to find file for shape $shapeType", e)
        }

    }

    internal fun loadShape(path: Path): Shape {
        try {
            val content = Files.readAllLines(path).joinToString("")
            return gson.fromJson(content, Shape::class.java)
        } catch (e: IOException) {
            throw RuntimeException("Impossible to load shape $path", e)
        }

    }
}
