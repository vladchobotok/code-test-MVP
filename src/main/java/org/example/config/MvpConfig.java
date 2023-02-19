package org.example.config;

import org.example.utils.GameAnalyzer;
import org.example.utils.GameReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MvpConfig {

    @Bean
    public GameReader gameReaderBean()
    {
        return new GameReader();
    }

    @Bean public GameAnalyzer gameAnalyzerBean()
    {
        return new GameAnalyzer();
    }
}
