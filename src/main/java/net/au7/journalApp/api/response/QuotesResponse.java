package net.au7.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuotesResponse {
    public String quote;
    public String author;
    public String work;
    public List<String> categories;
}