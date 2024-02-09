fun main() {
    // Entrada: tipo de cliente e datas
    val entrada = readln()

    // Separando o tipo de cliente das datas
    val (tipoCliente, datas) = entrada.split(":")

    // Lista de datas formatadas
    val datasFormatadas = datas.split(",").map { it.replace("(", "").replace(")", "") }

    // Função para calcular o preço total da estadia em um hotel
    fun calcularPrecoTotal(hotel: Hotel, datas: List<String>, tipoCliente: String): Int {
        var precoTotal = 0
        for (data in datas) {
            val diaDaSemana = data.substring(6, 9)
            val precoDia = when (diaDaSemana) {
                "mon", "tue", "wed", "thu" -> hotel.precoDiaSemana(tipoCliente)
                else -> hotel.precoFimSemana(tipoCliente)
            }
            precoTotal += precoDia
        }
        return precoTotal
    }

    // Lista de hotéis
    val hoteis = listOf(
        Hotel("Palm Plaza Resort", 3, 110, 90, 80, 80),
        Hotel("Elegance Haven Hotel", 4, 160, 60, 110, 50),
        Hotel("Azure Horizon Retreat", 5, 220, 150, 100, 40)
    )

    // Encontrar o hotel mais barato
    var hotelMaisBarato: Hotel? = null
    var menorPreco = Int.MAX_VALUE
    for (hotel in hoteis) {
        val precoTotal = calcularPrecoTotal(hotel, datasFormatadas, tipoCliente)
        if (precoTotal < menorPreco) {
            menorPreco = precoTotal
            hotelMaisBarato = hotel
        } else if (precoTotal == menorPreco && hotel.estrelas > hotelMaisBarato!!.estrelas) {
            hotelMaisBarato = hotel
        }
    }

    // Saída: nome do hotel mais barato
    println(hotelMaisBarato?.nome)
}

// Classe para representar um hotel
data class Hotel(
    val nome: String,
    val estrelas: Int,
    val precoDiaSemanaRegular: Int,
    val precoFimSemanaRegular: Int,
    val precoDiaSemanaVip: Int,
    val precoFimSemanaVip: Int
) {
    fun precoDiaSemana(tipoCliente: String): Int {
        return if (tipoCliente == "Regular") precoDiaSemanaRegular else precoDiaSemanaVip
    }

    fun precoFimSemana(tipoCliente: String): Int {
        return if (tipoCliente == "Regular") precoFimSemanaRegular else precoFimSemanaVip
    }
}
