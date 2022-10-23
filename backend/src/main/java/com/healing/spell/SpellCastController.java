package com.healing.spell;

import com.healing.spell.spellcast.SpellCastService;
import com.healing.spell.spellcast.request.SpellCastRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("spellcasting")
public class SpellCastController {

  private final SpellCastService spellCastService;

  @Autowired
  public SpellCastController(SpellCastService spellCastService) {
    this.spellCastService = spellCastService;
  }

  @PostMapping(produces = "application/json")
  public ResponseEntity<String> castSpell(@RequestBody SpellCastRequest data) {
    if (data.getSpellId() == null || data.getTargetId() == null) {
      return ResponseEntity.status(400).body("Missing spellId or targetId");
    }
    spellCastService.castSpell(data.getSpellId(), data.getTargetId());
    return ResponseEntity.ok().body("");
  }
}
