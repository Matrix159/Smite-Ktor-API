package matrix.com.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GodInformation(
    @SerialName("Ability1")
    val ability1: String,

    @SerialName("Ability2")
    val ability2: String,

    @SerialName("Ability3")
    val ability3: String,

    @SerialName("Ability4")
    val ability4: String,

    @SerialName("Ability5")
    val ability5: String,

    @SerialName("AbilityId1")
    val abilityId1: Long,

    @SerialName("AbilityId2")
    val abilityId2: Long,

    @SerialName("AbilityId3")
    val abilityId3: Long,

    @SerialName("AbilityId4")
    val abilityId4: Long,

    @SerialName("AbilityId5")
    val abilityId5: Long,

    @SerialName("Ability_1")
    val abilityDetails1: Ability,

    @SerialName("Ability_2")
    val abilityDetails2: Ability,

    @SerialName("Ability_3")
    val abilityDetails3: Ability,

    @SerialName("Ability_4")
    val abilityDetails4: Ability,

    @SerialName("Ability_5")
    val abilityDetails5: Ability,

    @SerialName("AttackSpeed")
    val attackSpeed: Double,

    @SerialName("AttackSpeedPerLevel")
    val attackSpeedPerLevel: Double,

    @SerialName("AutoBanned")
    val autoBanned: String,

    @SerialName("Cons")
    val cons: String,

    @SerialName("HP5PerLevel")
    val hp5PerLevel: Double,

    @SerialName("Health")
    val health: Long,

    @SerialName("HealthPerFive")
    val healthPerFive: Double,

    @SerialName("HealthPerLevel")
    val healthPerLevel: Double,

    @SerialName("Lore")
    val lore: String,

    @SerialName("MP5PerLevel")
    val mp5PerLevel: Double,

    @SerialName("MagicProtection")
    val magicProtection: Double,

    @SerialName("MagicProtectionPerLevel")
    val magicProtectionPerLevel: Double,

    @SerialName("MagicalPower")
    val magicalPower: Long,

    @SerialName("MagicalPowerPerLevel")
    val magicalPowerPerLevel: Double,

    @SerialName("Mana")
    val mana: Long,

    @SerialName("ManaPerFive")
    val manaPerFive: Double,

    @SerialName("ManaPerLevel")
    val manaPerLevel: Double,

    @SerialName("Name")
    val name: String,

    @SerialName("OnFreeRotation")
    val onFreeRotation: String,

    @SerialName("Pantheon")
    val pantheon: String,

    @SerialName("PhysicalPower")
    val physicalPower: Long,

    @SerialName("PhysicalPowerPerLevel")
    val physicalPowerPerLevel: Double,

    @SerialName("PhysicalProtection")
    val physicalProtection: Double,

    @SerialName("PhysicalProtectionPerLevel")
    val physicalProtectionPerLevel: Double,

    @SerialName("Pros")
    val pros: String,

    @SerialName("Roles")
    val roles: String,

    @SerialName("Speed")
    val speed: Long,

    @SerialName("Title")
    val title: String,

    @SerialName("Type")
    val type: String,

    val abilityDescription1: AbilityDescription,
    val abilityDescription2: AbilityDescription,
    val abilityDescription3: AbilityDescription,
    val abilityDescription4: AbilityDescription,
    val abilityDescription5: AbilityDescription,
    val basicAttack: AbilityDescription,

    @SerialName("godAbility1_URL")
    val godAbility1URL: String,

    @SerialName("godAbility2_URL")
    val godAbility2URL: String,

    @SerialName("godAbility3_URL")
    val godAbility3URL: String,

    @SerialName("godAbility4_URL")
    val godAbility4URL: String,

    @SerialName("godAbility5_URL")
    val godAbility5URL: String,

    @SerialName("godCard_URL")
    var godCardURL: String,

    @SerialName("godIcon_URL")
    val godIconURL: String,

    val id: Int,
    val latestGod: String,

    @SerialName("ret_msg")
    val retMsg: String?
)

@Serializable
data class AbilityDescription(
    val itemDescription: AbilityItemDescription
)

@Serializable
data class AbilityItemDescription(
    val cooldown: String,
    val cost: String,
    val description: String,
    val menuitems: List<LowerDescriptionValue>,
    val rankitems: List<LowerDescriptionValue>
)

@Serializable
data class Ability(
    @SerialName("Description")
    val description: AbilityDescription,

    @SerialName("Id")
    val id: Long,

    @SerialName("Summary")
    val summary: String,

    @SerialName("URL")
    val url: String
)
