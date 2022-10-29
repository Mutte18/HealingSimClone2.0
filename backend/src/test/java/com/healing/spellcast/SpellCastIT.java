package com.healing.spellcast;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.healing.integration.IntegrationTest;
import com.healing.spell.spellcast.request.SpellCastRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SpellCastIT extends IntegrationTest {

  @Test
  void shouldAddFlashHealSpellAction() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/spellcasting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new SpellCastRequest("1", "1"))))
        .andExpect(status().isOk());
  }
}
