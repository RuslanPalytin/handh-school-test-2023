class Player(attack: Int, protection: Int, health: Int, damage: Pair<Int, Int>) :
    Creature(attack = attack, protection = protection, health = health, damage = damage),
    IHit
{
    override fun getInfo() {
        println("\n---Игрок---")
        getCreatureInfo()
    }

    override fun hit(monster: Creature, checkThrowCube: Boolean) {
        println("Удар наносит Игрок")

        if(checkThrowCube){
            hitCreature(creature = monster)
        } else {
            println("Удар не успешный!")
        }
    }
}