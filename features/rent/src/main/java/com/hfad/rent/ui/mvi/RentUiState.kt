package com.hfad.rent.ui.mvi

data class PriceEntry(
    val timeRange: String,
    val weekdayPrice: String,
    val weekendPrice: String
)

data class RentUiState(
    val selectedHallType: String? = null,
    val needsAuth: Boolean = false,
    val hallTypes: List<String> = listOf(
        "Баскетбольный зал",
        "Волейбольная площадка",
        "Бадминтонный корт",
        "Зал свободного назначения 48 м²",
        "Танцевальный зал 80 м²",
        "Фитнес-зал 98 м²",
        "Зал для настольного тенниса 240 м²"
    ),
    val hallImages: Map<String, List<String>> = mapOf(
        "Баскетбольный зал" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-05/basketball-rent-gallery-1.jpg?itok=tn6uWQSd",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2022-10/basketball-rent-gallery-1.jpg?itok=DQXJX7pY",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-05/basketball-rent-gallery-4.jpg?itok=QTystz9C",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-05/basketball-rent-gallery-5.jpg?itok=FqZTEf0s",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2022-10/basketball-rent-gallery.jpg?itok=dyGAuMPv"
        ),
        "Волейбольная площадка" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-06/volleyball-rent-gallery-1.jpg?itok=7yEpwILq",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-06/volleyball-rent-gallery-6.jpg?itok=86rkz4P9",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-06/volleyball-rent-gallery-4.jpg?itok=-xD7pyHO",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2020-06/volleyball-rent-gallery-5.jpg?itok=79u3vEW9",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2022-10/%40alex.reportage%20%23206408_0.jpg?itok=M49caGNI",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2022-10/%40alex.reportage%20%23206400.jpg?itok=tYkObcwb"
        ),
        "Бадминтонный корт" to listOf(
            "https://cdn.terball.ru/storage/site/badminton/terball-about-1.jpg",
            "https://cdn.terball.ru/storage/site/badminton/terball-about-2.jpg",
            "https://cdn.terball.ru/storage/site/badminton/terball-about-3.jpg"
        ),
        "Зал свободного назначения 48 м²" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200381.jpg?itok=TXfJ7CZt",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/%D1%8466.jpg?itok=oHh4Qz_o",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200350.jpg?itok=bvviVuQa",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200420.jpg?itok=rcRsRJfj",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/%D1%8413.jpg?itok=fVWOix1d"
        ),
        "Танцевальный зал 80 м²" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200402.jpg?itok=RgzD72dr",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200398.jpg?itok=lwDqHG6T",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200336.jpg?itok=uSYye4Gm",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200395.jpg?itok=10RFzccG",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200399_0.jpg?itok=-BY-YHd6",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2021-09/insta%20-%20%40alex.reportage%200391_0.jpg?itok=PHI5gBjz"
        ),
        "Фитнес-зал 98 м²" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/%40alex.reportage%20img%2372858-2.jpg?itok=xwm2_CFw",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/%40alex.reportage%20img%2372838.jpeg?itok=44hMiyEF",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/%40alex.reportage%20img%2372905-2.jpg?itok=mkuxe4lk",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage353455.jpg?itok=yeKvroE1",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage353447.jpg?itok=_I9VzP5b",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage353452.jpg?itok=Eu5zfw6m"
        ),
        "Зал для настольного тенниса 240 м²" to listOf(
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage%20%23206342.jpg?itok=0Sibdwgv",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage359155.jpg?itok=QO6F9heE",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage%20%23206496.jpg?itok=_Bmq5Vgs",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage%20%23206503.jpg?itok=EsfuidUX",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage%20%23206349.jpg?itok=c0Oe6FDt",
            "https://terball.ru/sites/default/files/styles/crop_1000_800/public/2023-05/alex.reportage%20%23206499.jpg?itok=iFX7D-JZ"
        )
    ),
    val hallPrices: Map<String, List<PriceEntry>> = mapOf(
        "Баскетбольный зал" to listOf(
            PriceEntry("с 7:00 до 10:00", "6500", "6500"),
            PriceEntry("с 10:00 до 15:00", "5000", "15000"),
            PriceEntry("с 15:00 до 18:00", "6500", "15000"),
            PriceEntry("с 18:00 до 23:00", "15000", "15000"),
            PriceEntry("с 23:00 до 7:00", "5000", "5000")
        ),
        "Волейбольная площадка" to listOf(
            PriceEntry("с 7:00 до 10:00", "5500", "5500"),
            PriceEntry("с 10:00 до 15:00", "3500", "9000"),
            PriceEntry("с 15:00 до 18:00", "5500", "9000"),
            PriceEntry("с 18:00 до 23:00", "9000", "9000"),
            PriceEntry("с 23:00 до 7:00", "3500", "3500")
        ),
        "Бадминтонный корт" to listOf(
            PriceEntry("с 7:00 до 10:00", "1800", "1800"),
            PriceEntry("с 10:00 до 15:00", "1500", "2300"),
            PriceEntry("с 15:00 до 18:00", "1500", "2300"),
            PriceEntry("с 18:00 до 23:00", "2300", "2300"),
            PriceEntry("с 23:00 до 7:00", "1500", "1500")
        ),
        "Зал свободного назначения 48 м²" to listOf(
            PriceEntry("с 7:00 до 10:00", "900", "1100"),
            PriceEntry("с 10:00 до 17:00", "700", "1100"),
            PriceEntry("с 17:00 до 23:00", "1100", "1100"),
            PriceEntry("с 23:00 до 7:00", "700", "700")
        ),
        "Танцевальный зал 80 м²" to listOf(
            PriceEntry("с 7:00 до 10:00", "1200", "1700"),
            PriceEntry("с 10:00 до 17:00", "1400", "1700"),
            PriceEntry("с 17:00 до 23:00", "1700", "1700"),
            PriceEntry("с 23:00 до 7:00", "1400", "1400")
        ),
        "Фитнес-зал 98 м²" to listOf(
            PriceEntry("с 7:00 до 10:00", "1700", "2200"),
            PriceEntry("с 10:00 до 17:00", "1900", "2200"),
            PriceEntry("с 17:00 до 23:00", "2200", "2200"),
            PriceEntry("с 23:00 до 7:00", "1900", "1900")
        ),
        "Зал для настольного тенниса 240 м²" to listOf(
            PriceEntry("с 7:00 до 10:00", "3000", "5000"),
            PriceEntry("с 10:00 до 17:00", "3000", "5000"),
            PriceEntry("с 17:00 до 23:00", "5000", "5000"),
            PriceEntry("с 23:00 до 7:00", "3000", "3000")
        )
    ),
    val isRequestSent: Boolean = false,
    val errorMessage: String? = null
)
