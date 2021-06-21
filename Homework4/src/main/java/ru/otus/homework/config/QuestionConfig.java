package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "question")
@Component
public class QuestionConfig {
    private File file;
    private Credit credit;
    private LocaleConfig localeConfig;

    @Autowired
    public QuestionConfig(LocaleConfig localeConfig) {
        this.localeConfig = localeConfig;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public static class File {
        private String name;
        private String extension;

        public File() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    public static class Credit {
        private int count;

        public Credit() {
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public String getLocalizedFileName() {
        if (localeConfig.getName().equals("")) {
            return getFile().getName() + "." + getFile().getExtension();
        }
        else {
            return getFile().getName() + "_" + localeConfig.getName() + "." + getFile().getExtension();
        }
    }
}