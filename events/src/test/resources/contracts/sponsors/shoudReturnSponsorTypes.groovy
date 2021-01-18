package contracts.sponsors

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        Multiline description
        Given
        When 
        Then
""")
    request {
        method(GET())
        url("/events/2b116fca-37cf-478e-b1ff-f0c12eb91279")
    }
    response {
        status(OK())
        headers {
            contentType(applicationJson())
        }
        body(
            sponsorTypes: [[
                    "name": anyNonEmptyString(),
                  "price": anyDouble()
            ]]
        )
    }

}