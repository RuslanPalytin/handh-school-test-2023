private const val MIN_VALUE = 1
private const val MAX_VALUE = 30

fun main() {

    println("\n=== Данные для Игрока ===")

    val player = Player(
        attack = setAttackOrProtection(creature = CreatureEnum.PLAYER, creatureFiled = CreatureEnum.ATTACK),
        protection = setAttackOrProtection(creature = CreatureEnum.PLAYER, creatureFiled = CreatureEnum.PROTECTION),
        health = setHealth(),
        damage = setDamage()
    )

    println("\n=== Данные для Монстра ===")

    val monster = Monster(
        attack = setAttackOrProtection(creature = CreatureEnum.MONSTER, creatureFiled = CreatureEnum.ATTACK),
        protection = setAttackOrProtection(creature = CreatureEnum.MONSTER, creatureFiled = CreatureEnum.PROTECTION),
        health = setHealth(),
        damage = setDamage()
    )

    player.getInfo()
    monster.getInfo()

    startGame(player = player, monster = monster)
}

private fun startGame(player: Player, monster: Monster) {
    var round = 1

    println("========== START GAME ==========")

    while (player.maxHealthCheck && monster.maxHealthCheck){
        println("\t\tМенюшка: ")
        println("1 - Выпускаем кракенов на поле боя")
        println("2 - Хочу узнать, как чувствует себя Игрок")
        println("3 - Хочу узнать, как чувствует себя Монстр")
        println("0 - Не хочу больше играть\n")
        print("> ")

        when(readln()){
            "1" -> {
                hit(
                    round = round++,
                    dealsCreature = player,
                    takesCreature = monster
                )
                if(monster.maxHealthCheck) {
                    hit(
                        round = round++,
                        dealsCreature = monster,
                        takesCreature = player
                    )
                }
            }
            "2" -> {
                player.getInfo()
            }
            "3" -> {
                monster.getInfo()
            }
            "0" -> break

            else -> {
                println("Такого варината в менюшке нету(((")
            }
        }
    }

    if(whoWon(player, monster) is Player){
        println("Выиграло существо - Игрок")
    } else if (whoWon(player, monster) == null) {
        println("Ничего себе, у них победила дружба!")
    } else {
        println("Выиграло существо - Монстр")
    }

    println("\n=============GAME OVER=============")
}

fun hit(round: Int, dealsCreature : Creature, takesCreature: Creature): Boolean {

    var checkHealth = true
    println("\nРаунд $round")

    if (dealsCreature is Player && takesCreature is Monster) {

        val modifier = dealsCreature.attackModifier(creature = takesCreature)
        val checkThrowCube = dealsCreature.throwCube(modifier = modifier)
        dealsCreature.hit(monster = takesCreature, checkThrowCube = checkThrowCube)
        checkHealth = takesCreature.maxHealthCheck

    } else if (dealsCreature is Monster && takesCreature is Player) {

        val modifier = dealsCreature.attackModifier(creature = takesCreature)
        val checkThrowCube = dealsCreature.throwCube(modifier = modifier)
        dealsCreature.hit(player = takesCreature, checkThrowCube = checkThrowCube)
        checkHealth = takesCreature.maxHealthCheck
    }

    return checkHealth
}

fun whoWon(player: Player, monster: Monster): Creature?{

    return if(player.healthCount < monster.healthCount){
        player
    } else if(player.healthCount == monster.healthCount){
        if(player.health > monster.health){
            player
        } else if (player.health == monster.health) {
            null
        } else {
            monster
        }
    } else {
        monster
    }
}

private fun setAttackOrProtection(creature: CreatureEnum, creatureFiled: CreatureEnum): Int {

    print(
        "Введите значение " +
                "${if (creatureFiled == CreatureEnum.ATTACK) "атаки" else "защиты"} " +
                "для " +
                "${if (creature == CreatureEnum.MONSTER) "монстра" else "игрока"}: "
    )

    var value: Int

    while (true) {

        try {
            value = readln().toInt()

            if (value < MIN_VALUE || value > MAX_VALUE) {
                println("Введено неккоректое значение, повторите ввод")
                continue
            }
            break
        } catch (e: Exception) {
            println("Введено не число, повторите ввод")
            continue
        }
    }

    return value
}

private fun setHealth(): Int {

    var N: Int

    print("Введите максимальное значение здоровья: ")

    while (true) {
        try {
            N = readln().toInt()

            if (N < 0) {
                println("Введено значение меньше нуля, повторите ввод")
                continue
            }

            break
        } catch (e: Exception) {
            println("Введено не число, повторите ввод")
            continue
        }
    }


    var health: Int

    print("Введите значение здоровья: ")

    while (true) {
        try {
            health = readln().toInt()

            if (health < 0 || health > N) {
                println("Введено неккоректое значение, повторите ввод")
                continue
            }
            break
        } catch (e: Exception) {
            println("Введено не число, повторите ввод")
            continue
        }
    }

    return health
}

private fun setDamage(): Pair<Int, Int> {
    var lowerLimit: Int
    var upperLimit: Int

    print("Введите нижнюю границу значения урона: ")

    while (true) {
        try {
            lowerLimit = readln().toInt()

            if (lowerLimit < 0) {
                println("Введено значение меньше нуля, повторите ввод")
                continue
            }
            break
        } catch (e: Exception) {
            println("Введено неккоректое значение, повторите ввод")
            continue
        }
    }

    print("Введите верхнюю границу значения урона: ")

    while (true) {
        try {
            upperLimit = readln().toInt()

            if (upperLimit < lowerLimit) {
                println("Введено значение меньше нижней границы, повторите ввод")
                continue
            }
            break
        } catch (e: Exception) {
            println("Введено неккоректое значение, повторите ввод")
            continue
        }
    }

    return Pair(lowerLimit, upperLimit)
}