package com.schmitt.reactive.java.src.model.person;

import org.junit.jupiter.api.Test;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonHashCodeTest {

    @Test
    void twoParents_matchingRequiredAttributes_noExtraAttributes_expectHashCodesMatch() {
        // arrange
        final Parent parentOne = new Parent("Chris", 100);
        final Parent parentTwo = new Parent("Chris", 100);

        // act
        final int actualOne = parentOne.hashCode();
        final int actualTwo = parentTwo.hashCode();

        // assert
        assertThat(actualOne).isEqualTo(actualTwo);
    }

    @Test
    void twoParents_matchingRequiredAttributes_withExtraAttributes_expectHashCodesMatch() {
        // arrange
        final Parent parentOne = new Parent("Chris", 100);
        final Parent parentTwo = new Parent("Chris", 100);
        final Parent parentThree = new Parent("Chris", 100);
        parentThree.setPhoneNumber(123456789012L);

        // act
        final int actualOne = parentOne.hashCode();
        final int actualTwo = parentTwo.hashCode();
        final int actualThree = parentThree.hashCode();

        // assert
        assertThat(actualOne).isEqualTo(actualTwo).isEqualTo(actualThree);
    }

    /*@Test
    void fields_v1() {
        final UUID personVersion = Person.getVERSION_IDENTIFIER();
        final UUID parentV1Version = ParentV1.getVERSION_IDENTIFIER();
        final UUID parentV2Version = ParentV2.getVERSION_IDENTIFIER();

        assertThat(personVersion.toString()).isEqualTo("1234");
        assertThat(parentV1Version.toString()).isEqualTo("1234");
        assertThat(parentV2Version.toString()).isEqualTo("1234");
    }*/

    @Test
    void toStringMd5() {
        // arrange
        final ParentV1 parentV1 = new ParentV1("HashNameValue", 0);
        final ParentV1 parentV12 = new ParentV1("HashNameValue", 0);
        final ParentV2 parentV2 = new ParentV2("HashNameValue", 0);

        // act
        final String actual1 = parentV1.toString();
        final String oneHash = DigestUtils.md5Hex(actual1);
        final String actual12 = parentV12.toString();
        final String oneHash2 = DigestUtils.md5Hex(actual12);
        final String actual2 = parentV2.toString();
        final String twoHash = DigestUtils.md5Hex(actual2);

        // assert
        //assertThat(actual1).isEqualTo(actual2);
        //assertThat(oneHash).isEqualTo(twoHash);
        assertThat(oneHash).isEqualTo(oneHash2);
    }
}
