import kotlin.math.ceil
import kotlin.random.Random

abstract class Creature {

    constructor(attack: Int, protection: Int, health: Int, damage: Pair<Int, Int>) {
        this.attack = attack
        this.protection = protection
        this.health = health
        this.maxHealth = health
        this.damage = damage
    }

    var attack: Int
    var protection: Int
    var damage: Pair<Int, Int>
    var maxHealth: Int
    var health: Int
        set(value) {
            field = if (value >= 0) {
                value
            } else {
                0
            }
        }

    var healthCount: Int = 0
    var maxHealthCheck = true

    protected fun getCreatureInfo() {
        println("Атака: ${this.attack}")
        println("Защита: ${this.protection}")
        println("Здоровье: ${this.health}")
        println("Урон: ${this.damage.first} - ${this.damage.second}")
        println("Количесвто использованных хилов: ${this.healthCount} из 4\n")
    }

    protected fun hitCreature(creature: Creature) {
        val hit = Random.nextInt(this.damage.first, this.damage.second)
        creature.health -= hit
        println("Удар успешный, существо нанесло $hit урона оппоненту")
        creature.maxHealthCheck = checkHealCreature(creature)
    }

    private fun checkHealCreature(creature: Creature): Boolean {
        if (creature.health == 0) {
            if (creature.healthCount >= MAX_HEALTH) {
                return false
            }
            print("У существа не осталось здоровья, захилить его? (Да / Нет)")
            when (readln()) {
                "Да" -> {
                    creature.healthCount++
                    creature.health = (ceil(creature.maxHealth * 0.3)).toInt()
                }

                "Нет" -> {
                    return false
                }

                else -> {
                    println("Введено неккоректное значение, существо проиграло!")
                    return false
                }
            }
        }
        return true
    }

    fun throwCube(modifier: Int): Boolean {

        var countThrowCube = 0

        do {
            val throwCube = Random.nextInt(MIN_VALUE_CUBE, MAX_VALUE_CUBE)
            if (throwCube >= SUCCESSFUL_CUBE_THROW) {
                return true
            }
            countThrowCube++
        } while (modifier > countThrowCube)

        return false
    }

    fun attackModifier(creature: Creature): Int {
        return this.attack - creature.protection + 1
    }

    companion object {
        private const val SUCCESSFUL_CUBE_THROW = 5
        private const val MAX_HEALTH = 4
        private const val MIN_VALUE_CUBE = 1
        private const val MAX_VALUE_CUBE = 6
    }
}