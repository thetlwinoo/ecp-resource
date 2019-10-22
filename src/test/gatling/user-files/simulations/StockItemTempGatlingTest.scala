import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the StockItemTemp entity.
 */
class StockItemTempGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the StockItemTemp entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all stockItemTemps")
            .get("/api/stock-item-temps")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new stockItemTemp")
            .post("/api/stock-item-temps")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "id":null
                , "stockItemName":"SAMPLE_TEXT"
                , "vendorCode":"SAMPLE_TEXT"
                , "vendorSKU":"SAMPLE_TEXT"
                , "barcode":"SAMPLE_TEXT"
                , "barcodeTypeId":null
                , "barcodeTypeName":"SAMPLE_TEXT"
                , "productType":"SAMPLE_TEXT"
                , "productCategoryId":null
                , "productCategoryName":"SAMPLE_TEXT"
                , "productAttributeSetId":null
                , "productAttributeId":null
                , "productAttributeValue":"SAMPLE_TEXT"
                , "productOptionSetId":null
                , "productOptionId":null
                , "productOptionValue":"SAMPLE_TEXT"
                , "modelName":"SAMPLE_TEXT"
                , "modelNumber":"SAMPLE_TEXT"
                , "materialId":null
                , "materialName":"SAMPLE_TEXT"
                , "shortDescription":null
                , "description":null
                , "productBrandId":null
                , "productBrandName":"SAMPLE_TEXT"
                , "highlights":null
                , "searchDetails":null
                , "careInstructions":null
                , "dangerousGoods":"SAMPLE_TEXT"
                , "videoUrl":"SAMPLE_TEXT"
                , "unitPrice":null
                , "remommendedRetailPrice":null
                , "currencyCode":"SAMPLE_TEXT"
                , "quantityOnHand":"0"
                , "warrantyPeriod":"SAMPLE_TEXT"
                , "warrantyPolicy":"SAMPLE_TEXT"
                , "warrantyTypeId":null
                , "warrantyTypeName":"SAMPLE_TEXT"
                , "whatInTheBox":null
                , "itemLength":"0"
                , "itemWidth":"0"
                , "itemHeight":"0"
                , "itemWeight":null
                , "itemPackageLength":"0"
                , "itemPackageWidth":"0"
                , "itemPackageHeight":"0"
                , "itemPackageWeight":null
                , "itemLengthUnitMeasureId":null
                , "itemLengthUnitMeasureCode":"SAMPLE_TEXT"
                , "itemWidthUnitMeasureId":null
                , "itemWidthUnitMeasureCode":"SAMPLE_TEXT"
                , "itemHeightUnitMeasureId":null
                , "itemHeightUnitMeasureCode":"SAMPLE_TEXT"
                , "itemWeightUnitMeasureId":null
                , "itemWeightUnitMeasureCode":"SAMPLE_TEXT"
                , "itemPackageLengthUnitMeasureId":null
                , "itemPackageLengthUnitMeasureCode":"SAMPLE_TEXT"
                , "itemPackageWidthUnitMeasureId":null
                , "itemPackageWidthUnitMeasureCode":"SAMPLE_TEXT"
                , "itemPackageHeightUnitMeasureId":null
                , "itemPackageHeightUnitMeasureCode":"SAMPLE_TEXT"
                , "itemPackageWeightUnitMeasureId":null
                , "itemPackageWeightUnitMeasureCode":"SAMPLE_TEXT"
                , "noOfPieces":"0"
                , "noOfItems":"0"
                , "manufacture":"SAMPLE_TEXT"
                , "specialFeactures":null
                , "productComplianceCertificate":"SAMPLE_TEXT"
                , "genuineAndLegal":null
                , "countryOfOrigin":"SAMPLE_TEXT"
                , "usageAndSideEffects":null
                , "safetyWarnning":null
                , "sellStartDate":"2020-01-01T00:00:00.000Z"
                , "sellEndDate":"2020-01-01T00:00:00.000Z"
                , "status":"0"
                , "lastEditedBy":"SAMPLE_TEXT"
                , "lastEditedWhen":"2020-01-01T00:00:00.000Z"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_stockItemTemp_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created stockItemTemp")
                .get("${new_stockItemTemp_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created stockItemTemp")
            .delete("${new_stockItemTemp_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
