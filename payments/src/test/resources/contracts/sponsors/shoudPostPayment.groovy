import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method(POST())
        url("/payments")
        headers {
            contentType(applicationJson())
        }
        body(
                holder: $(anyNonEmptyString()),
                number: $(regex('[0-9]{12,16}')),
                cvc: $(regex('[0-9]{3}')),
                expiration: $(regex('[0-9]{2}/[0-9]{2}')),
                amount: $(anyDouble()),
                note: $(anyNonEmptyString())
        )
    }

    response {
        status(CREATED())
        headers {
            header('location', anyUrl())
        }
    }
}