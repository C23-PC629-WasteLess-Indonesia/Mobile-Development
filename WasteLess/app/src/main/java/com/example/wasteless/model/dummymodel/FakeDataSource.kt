package com.example.wasteless.model.dummymodel

import com.example.wasteless.R

object FakeDataSource {
    val dummyUser = listOf(
        User(
            1,
            "Budi Sudarsono",
            "Budi@mail.com",
            "123123",
            "laki-laki",
            "Semarang, Indonesia",
            R.drawable.rotisobek,
            "098987909878"
        ),
        User(
            2,
            "Budi Sudarsono Maulana",
            "Budi@mail.com",
            "123123",
            "laki-laki",
            "bandung, Indonesia",
            R.drawable.rotisobek,
            "098987909878"
        ),
        User(
            3,
            "Budi jamil",
            "Budi@mail.com",
            "123123",
            "laki-laki",
            "Semarang, Indonesia",
            R.drawable.rotisobek,
            "098987909878"
        ),
    )

    val dummyFood = listOf(
        Food(
            1,
            "Roti Sobek",
            "Roti sobek sudah menjadi salah satu variasi roti yang paling digemari oleh masyarakat. Mulai dari roti sobek manis, roti sobek isi coklat, hingga roti dengan isi daging. Roti sangat bervariasi dari bentuk, rasa, tekstur hingga ukuran.",
            "1 Loyang",
            "12/12/2023",
            R.drawable.rotisobek,
            "Semarang, Indonesia",
            -6.998721,
            110.467918,
            dummyUser[0],
            "12/05/2023",
            "Roti",
            true
        ),
        Food(
            2,
            "Roti Sobek",
            "Roti sobek sudah menjadi salah satu variasi roti yang paling digemari oleh masyarakat. Mulai dari roti sobek manis, roti sobek isi coklat, hingga roti dengan isi daging. Roti sangat bervariasi dari bentuk, rasa, tekstur hingga ukuran.",
            "1 Loyang",
            "12/12/2023",
            R.drawable.rotisobek,
            "Semarang, Indonesia",
            -6.998721,
            110.467918,
            dummyUser[0],
            "12/05/2023",
            "Roti",
            false
        ),
        Food(
            3,
            "Roti Sobek",
            "Roti sobek sudah menjadi salah satu variasi roti yang paling digemari oleh masyarakat. Mulai dari roti sobek manis, roti sobek isi coklat, hingga roti dengan isi daging. Roti sangat bervariasi dari bentuk, rasa, tekstur hingga ukuran.",
            "1 Loyang",
            "12/12/2023",
            R.drawable.rotisobek,
            "Semarang, Indonesia",
            -6.998721,
            110.467918,
            dummyUser[2],
            "12/05/2023",
            "Roti",
            true
        ),
        Food(
            4,
            "Roti Sobek",
            "Roti sobek sudah menjadi salah satu variasi roti yang paling digemari oleh masyarakat. Mulai dari roti sobek manis, roti sobek isi coklat, hingga roti dengan isi daging. Roti sangat bervariasi dari bentuk, rasa, tekstur hingga ukuran.",
            "1 Loyang",
            "12/12/2023",
            R.drawable.rotisobek,
            "Semarang, Indonesia",
            -6.998721,
            110.467918,
            dummyUser[1],
            "12/05/2023",
            "Roti",
            true
        )
    )
    val dummyHistory = listOf(
        History(
            1,
            dummyUser[1],
            dummyFood[2],
            false
        ),
        History(
            2,
            dummyUser[2],
            dummyFood[2],
            false
        )
    )
}
