cd ../../android-team-service/
rm -rf static/js
cd ../compose-multiplatform/atlasv-android-web-client/
./gradlew :page-home:jsBrowserDistribution
./gradlew :page-upload-file:jsBrowserDistribution
./gradlew :page-log-viewer:jsBrowserDistribution
./gradlew :page-purchase-products:jsBrowserDistribution
./gradlew :page-perf-overview:jsBrowserDistribution
./gradlew :page-disk-report:jsBrowserDistribution
./gradlew publishWebSite