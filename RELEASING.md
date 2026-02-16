# Releasing

## Code check
1. Run gradle tasks:

```shell
./gradlew kolor-picker:checkLegacyAbi
```

```shell
./gradlew spotlessCheck
```

Update ABI if necessary:

```shell
./gradlew kolor-picker:updateLegacyAbi
```

## New veresion
1. Update version (`VERSION_NAME`) in `gradle.properties`
2. Commit changes ("release vX.X.X") and create annotated tag (tag -a vX.X.X -m "message")
3. Push commit and tag to repo
4. Release via `./gradlew publishToMavenCentral --no-configuration-cache`
5. Create [GitHub release](https://github.com/krizzu/kolor-picker/releases)
6. Publish deployment on [Sonatype Central](https://central.sonatype.com/publishing/deployments)