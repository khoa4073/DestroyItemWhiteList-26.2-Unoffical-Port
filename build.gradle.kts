plugins {
    `maven-publish`
    id("dev.isxander.modstitch.base")
    //id("dev.kikugie.j52j")
    //id("me.modmuss50.mod-publish-plugin")
}

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name").toString()
    val version = property("mod.version").toString()
    val group = property("mod.group").toString()
}

class ModDependencies {
    operator fun get(name: String) = property("deps.$name").toString()
}

val mod = ModData()
val deps = ModDependencies()
val mcVersion = stonecutter.current.version
val mcDep = property("mod.mc_dep").toString()

version = "${mod.version}+$mcVersion"
group = mod.group
base { archivesName.set(mod.id) }

repositories {
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
    }
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
    maven("https://maven.parchmentmc.org")
    mavenCentral()
}

dependencies {
    modstitchModApi("com.terraformersmc:modmenu:${deps["mod_menu"]}")
    modstitchModImplementation("dev.isxander:yet-another-config-lib:${deps["yacl"]}")
    modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${deps["fabric_api"]}")
    implementation("org.quiltmc.parsers:gson:0.2.1")

    if (stonecutter.eval(mcVersion, "1.20.4")) {
        modstitchModImplementation("eu.pb4:placeholder-api:2.4.0-pre.1+1.20.4")
    } else if (stonecutter.eval(mcVersion, "1.21.5")) {
        modstitchModImplementation("eu.pb4:placeholder-api:2.6.1+1.21.5")
    }
}

modstitch {
    minecraftVersion = mcVersion
    metadata {
        modId = mod.id
        modName = mod.name
        modVersion = "${mod.version}+$mcVersion"
        modGroup = mod.group
        modLicense = "CC0-1.0"
        modAuthor = "Danrus110_, kreo_gen"
    }
    classTweakerName = "diwl.ct"
    val accessWidener = rootProject.file("src/main/resources/aws/${stonecutter.current.project}.accesswidener")
    if (accessWidener.exists()) {
        classTweaker.set(accessWidener)
    }
    loom {
        fabricLoaderVersion = deps["fabric_loader"]
        configureLoom {
            decompilers {
                get("vineflower").apply {
                    options.put("mark-corresponding-synthetics", "1")
                }
            }
            runConfigs.all {
                ideConfigGenerated(true)
                vmArgs("-Dmixin.debug.export=true")
                runDir = "../../run"
            }
            if (!stonecutter.eval(mcVersion, "26.2")) {
                mixin {
                    useLegacyMixinAp = true
                    defaultRefmapName = "diwl.refmap.json"
                }
            }
        }
    }
    if (!stonecutter.eval(mcVersion, "26.2")) {
        parchment {
            mappingsVersion = deps["parchment"]
            minecraftVersion = mcVersion
        }
    }
    mixin {
        addMixinsToModManifest = false
        configs.register("diwl")
    }
}

java {
    withSourcesJar()
    val java = when {
        stonecutter.eval(mcVersion, ">=26.2") -> JavaVersion.VERSION_25
        stonecutter.eval(mcVersion, ">=1.20.6") -> JavaVersion.VERSION_21
        else -> JavaVersion.VERSION_17
    }
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.processResources {
    inputs.property("id", mod.id)
    inputs.property("name", mod.name)
    inputs.property("version", mod.version)
    inputs.property("mcdep", mcDep)
    inputs.property("minecraft_version", stonecutter.current.version.toString())

    val map = mapOf(
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "mcdep" to mcDep,
        "minecraft_version" to stonecutter.current.version.toString()
    )

    filesMatching("fabric.mod.json") { expand(map) }
        doLast {
            if (stonecutter.eval(mcVersion, "26.2")) {
                val mixinConfig = destinationDir.resolve("diwl.mixins.json")
                @Suppress("UNCHECKED_CAST")
                val mixinMetadata = groovy.json.JsonSlurper().parse(mixinConfig) as MutableMap<String, Any?>
                mixinMetadata.remove("refmap")
                mixinConfig.writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(mixinMetadata)))
            }
        }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(modstitch.finalJarTask.flatMap { it.archiveFile })
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}"))
    dependsOn("build")
}

/*
publishMods {
    file = tasks.remapJar.get().archiveFile
    additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
    displayName = "${mod.name} ${mod.version} for $mcVersion"
    version = mod.version
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    dryRun = providers.environmentVariable("MODRINTH_TOKEN")
        .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }

    curseforge {
        projectId = property("publish.curseforge").toString()
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }
}
*/
/*
publishing {
    repositories {
        maven("...") {
            name = "..."
            credentials(PasswordCredentials::class.java)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "${property("mod.group")}.${mod.id}"
            artifactId = mod.version
            version = mcVersion

            from(components["java"])
        }
    }
}
*/

modstitch {
    minecraftVersion = mcVersion
    classTweakerName = "diwl.ct"
    val accessWidener = rootProject.file("src/main/resources/aws/${stonecutter.current.project}.accesswidener")
    if (accessWidener.exists()) {
        classTweaker.set(accessWidener)
    }
    loom {
        fabricLoaderVersion = deps["fabric_loader"]
        configureLoom {
            decompilers {
                get("vineflower").apply {
                    options.put("mark-corresponding-synthetics", "1")
                }
            }
            runConfigs.all {
                ideConfigGenerated(true)
                vmArgs("-Dmixin.debug.export=true")
                runDir = "../../run"
            }
        }
    }
    if (!stonecutter.eval(mcVersion, "26.2")) {
        parchment {
            mappingsVersion = deps["parchment"]
            minecraftVersion = mcVersion
        }
    }
}