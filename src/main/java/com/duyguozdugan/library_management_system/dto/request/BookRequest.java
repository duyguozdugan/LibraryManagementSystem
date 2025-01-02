package com.duyguozdugan.library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

        @NotNull
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        private String title;

        @NotNull
        @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
        private String author;
        private String category;

        public @NotNull @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters") String getTitle() {
                return title;
        }

        public void setTitle(@NotNull @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters") String title) {
                this.title = title;
        }

        public @NotNull @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters") String getAuthor() {
                return author;
        }

        public void setAuthor(@NotNull @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters") String author) {
                this.author = author;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }
}
