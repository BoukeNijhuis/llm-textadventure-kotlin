package nl.boukenijhuis.provider;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public abstract class AbstractOpenAiProvider extends AbstractProvider {

    public AbstractOpenAiProvider(String model) {
        super(model);
    }

    @Override
    public ChatModel getChatModel() {

        return OpenAiChatModel.builder()
                .apiKey(System.getenv(getEnvironmentVariableValue()))
                .baseUrl(getBaseURL())
                .modelName(model)
                // prevents rate limiter logging
                .maxRetries(1)
                .build();
    }

    abstract public String getEnvironmentVariableValue();
    abstract public String getBaseURL();
}
