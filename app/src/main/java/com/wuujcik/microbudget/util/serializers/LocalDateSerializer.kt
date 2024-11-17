package com.wuujcik.microbudget.util.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "LocalDateSerializer",
            PrimitiveKind.STRING,
        )

    override fun serialize(
        encoder: Encoder,
        value: LocalDate,
    ) = encoder.encodeString(
        formatter.format(value),
    )

    override fun deserialize(decoder: Decoder): LocalDate =
        LocalDate.parse(
            decoder.decodeString(),
            formatter,
        )
}

typealias LocalDate =
        @Serializable(LocalDateSerializer::class)
        LocalDate
