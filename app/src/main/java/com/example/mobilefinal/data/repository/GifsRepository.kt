package com.example.mobilefinal.data.repository

class GifsRepository {
    private val gifsMap: HashMap<String, String> = hashMapOf(
        "2ORFMoR" to "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExOXVleTRzb2Y2MHNmZGV4cWxwMGlncnp0ZmdlZzdvemQyZjc3eGozcyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/KdkeiEDSPOvpWMryCJ/giphy.gif",
        "2Qh2J1e" to "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExMGdmN25nYmNkZGJ5anRjenRnbmk1dmEzZTNiY3BheW1pZHRkZzB1aCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/l0QHadT43Y0qT2dfAD/giphy.gif",
        "3eGE2JC" to "https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExYTV4NjY5bzc3NGk1a2hicnQ0bzdpMmtqMmpjeGEzbW1nb3Y2d2xzdCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/NNSG5lLMnRcVWERY65/giphy.gif",
        "3tAXPQ6" to "https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExbzBocTB4NmJ5M2xibTM4azZnaGswZGEzdHhxdmZ0d3EzZm1raTNleiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/P7YSW1hGgmQn4i7zcy/giphy.gif",
        "3TZduzM" to "https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExbjVyYnVlM3FpeXg3c3lzb2J2ZHZqbW9lc2poaGk1aXZ1aG83MWwwNSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/LbdhC9QFU3sxfGdagj/giphy.gif",
        "3XFdb1Z" to "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExNTk3cDg1bGZkNnFwaHZsdTM1dnE4OGRmdXludWtqbzAyYjRpeDE5OCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/hwCLUuKXvpzpG6Hmf8/giphy.gif",
        "4dF3maG" to "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExNDVrNjM1MmxrcmYwd3N6MTYwNTB0ODA4dzhraG02d3c0OG5hcHA4cCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/tkYVzzYjYYEjhF8dNN/giphy.gif",
        "4dUn2iv" to "https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExdHkyM2JpbHFicXgxbXlmYWFjMHNidXV4M3BvMHdranF2ZzJ2ZzA1byZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/vP3p4zfSD40so011Ti/giphy.gif"
    )

    fun getGifUrlById(id: String): String? {
        return gifsMap[id]
    }
}