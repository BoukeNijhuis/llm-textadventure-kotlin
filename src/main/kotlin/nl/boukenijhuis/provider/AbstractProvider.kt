package nl.boukenijhuis.provider;

public abstract class AbstractProvider implements Provider {

    protected String model;

    public AbstractProvider(String model) {
        setModel(model);
    }

    // makes sure there is always a selected model
    protected void setModel(String model) {
        if (model == null || model.isBlank()) {
            this.model = this.getDefaultModel();
        } else {
            this.model = model;
        }
    }

    public String getModel() {
        return model;
    }

    @Override
    public String handleException(Exception e) throws Exception {
        // ignore the rate limiter
        if (e.getMessage().contains(getRateLimitMessage())) {
            System.out.print(".");
            // sleep for 1 second
            Thread.sleep(1000);
            // no new command, just retry
            return null;
        } else {
            throw e;
        }
    }

    abstract public String getRateLimitMessage();
}
