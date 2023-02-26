package com.healing.spellcast;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.healing.integration.IntegrationTest;
import com.healing.spell.spellbook.FlashHeal;
import com.healing.spell.spellcast.request.SpellCastRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SpellCastIntegrationTest extends IntegrationTest {

  @Test
  void shouldCastSpellWithValidSpellAndTarget() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/spellcasting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        new SpellCastRequest(new FlashHeal().getSpellId(), "PLAYER0"))))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturn404WithInvalidSpellId() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/spellcasting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new SpellCastRequest("99", "PLAYER0"))))
        .andExpect(status().is(404));
  }

  @Test
  void shouldReturn404WithInvalidTargetId() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/spellcasting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    mapper.writeValueAsString(
                        new SpellCastRequest(new FlashHeal().getSpellId(), "GG123"))))
        .andExpect(status().is(404));
  }
}
