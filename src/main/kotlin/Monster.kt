class Monster(attack: Int, protection: Int, health: Int, damage: Pair<Int, Int>) :
    Creature(attack = attack, protection = protection, health = health, damage = damage),
    IHit
{
    override fun getInfo() {
        println("\n---Монстр---")
        getCreatureInfo()
    }

    override fun hit(player: Creature, checkThrowCube: Boolean) {
        println("Удар наносит Монстр")

        if(checkThrowCube){
            hitCreature(creature = player)
        } else {
            println("Удар не успешный!")
        }
    }
}