package dev.malonso.springcontracttestlab.sponsors.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class SponsorCreate {
    @NonNull
    protected String name;
}
