# Releasing

1. Run gradle tasks `apiCheck` and `spotlessCheck`
2. Update version (`VERSION_NAME`) in `gradle.properties`
3. Commit changes ("release vX.X.X") and create annotated tag (tag -a vX.X.X -m "message")
4. Push commit and tag to repo
5. Release via `./gradlew publishToMavenCentral --no-configuration-cache`
6. Create [GitHub release](https://github.com/krizzu/kolor-picker/releases)
7. Publish deployment on [Sonatype Central](https://central.sonatype.com/publishing/deployments)