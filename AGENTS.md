Repo: playable-species-demo — compact agent notes

- What this is: a Fabric (Minecraft) mod template using fabric-loom. Key build files: build.gradle, gradle.properties, fabric.mod.json.

- Required JDK: Java 25. build.gradle sets compilation target to 25 and fabric.mod.json requires java >=25. Verify with `java -version` and use a JDK 25 build when running Gradle/compiling.

- Always use the included Gradle wrapper. Unix: `chmod +x ./gradlew` (if needed) && `./gradlew build`. Windows: `gradlew.bat build`. CI runs these exact steps.

- Common Gradle tasks you'll actually need:
  - Build: `./gradlew build`
  - Dev client (runs Minecraft, will download runtime): `./gradlew runClient`
  - Dev server: `./gradlew runServer`
  - Clean: `./gradlew clean`
  - Tests: none are configured in this template (`./gradlew test` is typically a no-op).

- Gradle quirks / repo specifics:
  - `org.gradle.configuration-cache=false` is set in gradle.properties due to fabric-loom compatibility — do not enable the configuration cache unless you know the Loom issue is resolved.
  - `org.gradle.jvmargs=-Xmx1G` is set; running the client can require more memory (increase if you see OOMs).
  - Gradle wrapper version is pinned in `gradle/wrapper/gradle-wrapper.properties` (9.4.1 here).

- Source layout and entrypoints:
  - Main entrypoint: `nessa.plsp.PlayableSpecies` (src/main/java/...)
  - Client entrypoint: `nessa.plsp.client.PlayableSpeciesClient` (src/client/java/...)
  - Mixin configs: `src/main/resources/playable-species.mixins.json` and `src/client/resources/playable-species.client.mixins.json`
  - The mod metadata (fabric.mod.json) uses `${version}` expanded at build time from the Gradle project version (set via gradle.properties `mod_version`).

- Versioning / resource notes: bump `mod_version` in gradle.properties to change the mod version; processResources expands that into fabric.mod.json.

- CI expectations (follow these locally to reproduce CI): GitHub Actions uses ubuntu-24.04, JDK 25 (microsoft distribution), validates the wrapper, makes `gradlew` executable and runs `./gradlew build`.

- Git / push notes for automated agents:
  - Repo may not be initialized as git in workspace. If asked to push, use the non-destructive sequence:
    1. `git init` (if not a repo yet)
    2. `git add .`
    3. `git commit -m "Add AGENTS.md: concise agent instructions"`
    4. `git branch -M main`
    5. `git remote add origin git@github.com:BloodyNessa/playable-species-demo.git`
    6. `git push -u origin main`
  - Do not force-push. Ensure the SSH key is available to the environment (SSH agent / ~/.ssh) before pushing; pushes will fail silently if SSH isn't loaded.

- What to check first when something breaks:
  - Confirm `java -version` === 25. Compiler errors about "invalid target release: 25" mean the JDK is wrong.
  - Confirm `./gradlew` is executable and you used the wrapper rather than system `gradle`.
  - Check `.github/workflows/build.yml`, `gradle.properties`, and `build.gradle` for version variables (minecraft_version, loader_version, loom_version, fabric_api_version).

- Don'ts / gotchas an agent might try incorrectly:
  - Don't enable Gradle configuration-cache (it's explicitly disabled). 
  - Don't commit build/ or run/ (they're in .gitignore).
  - Don't assume tests exist — there are none in this template.

- Where to read next: README.md, build.gradle, gradle.properties, fabric.mod.json, and .github/workflows/build.yml — these hold the executable truths for this repo.
