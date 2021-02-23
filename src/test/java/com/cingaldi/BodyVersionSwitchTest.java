package com.cingaldi;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BodyVersionSwitchTest {

    @Test
    void givenVersion_itParses() {

        var result = new BodyVersionSwitch().withVersion("application/vnd.glovoapp.v1", "");

        assertThat(result.getVersion()).isEqualTo("v1");
    }

    @Test
    void givenVersionAnfPlus_itParses() {

        var result = new BodyVersionSwitch().withVersion("application/vnd.glovoapp.v1+json", "");

        assertThat(result.getVersion()).isEqualTo("v1");
    }

    @Test
    void givenNoVersion_appliesDefault() {

        var result = new BodyVersionSwitch().withVersion("application/json", "v0");

        assertThat(result.getVersion()).isEqualTo("v0");
    }


    @Test
    void givenDefaultVersion_isDefault() {

        var result = new BodyVersionSwitch().withVersion("application/json", "v0");

        assertThat(result.isDefault()).isTrue();
    }

    @Test
    void givenVersion_whenMatch_thenCallback() {
        var result = new BodyVersionSwitch()
                .withVersion("application/vnd.glovoapp.v1+json", "")
                .whenMatch("v1", () -> new ResponseEntity(HttpStatus.OK))
                .getResult();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void givenDefaultVersion_whenMatch_thenCallback() {
        var result = new BodyVersionSwitch()
                .withVersion("application/vnd.glovoapp.v1+json", "v1")
                .whenDefault(() -> new ResponseEntity(HttpStatus.OK))
                .getResult();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void givenDefaultVersion_whenNoMatch_theOrCallback() {
        var result = new BodyVersionSwitch()
                .withVersion("application/vnd.glovoapp.v2+json", "v1")
                .whenDefault(() -> new ResponseEntity(HttpStatus.OK))
                .orMatch("v2", () -> new ResponseEntity(HttpStatus.ACCEPTED))
                .getResult();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }


    @Test
    void givenDefaultVersion_whenNoMatchTwoTimes_theOrCallback() {
        var result = new BodyVersionSwitch()
                .withVersion("application/vnd.glovoapp.v3+json", "v1")
                .whenDefault(() -> new ResponseEntity(HttpStatus.OK))
                .orMatch("v2", () -> new ResponseEntity(HttpStatus.ACCEPTED))
                .orMatch("v3", () -> new ResponseEntity(HttpStatus.BAD_REQUEST))
                .getResult();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }



    @Test
    void givenVersion_whenNoneMatched_thenThrows() {

        assertThrows(Exception.class, () -> {
            new BodyVersionSwitch()
                    .withVersion("application/vnd.glovoapp.v3+json", "v1")
                    .whenDefault(() -> new ResponseEntity(HttpStatus.OK))
                    .orMatch("v2", () -> new ResponseEntity(HttpStatus.ACCEPTED))
                    .getResult();
        });

    }
}