package nl.boukenijhuis.provider

abstract class AbstractProvider(model:String) : Provider {

    override lateinit var model: String

    // makes sure there is always a selected model
    init {
        if (model.isBlank()) {
            this.model = this.defaultModel
        } else {
            this.model = model
        }
    }

    @Throws(Exception::class)
    override fun handleException(e: Exception): String? {
        // ignore the rate limiter
        if (e.message!!.contains(this.rateLimitMessage)) {
            print(".")
            // sleep for 1 second
            Thread.sleep(1000)
            // no new command, just retry
            return null
        } else {
            throw e
        }
    }

    abstract val rateLimitMessage: String
}
