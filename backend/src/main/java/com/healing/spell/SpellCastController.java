package com.healing.spell;

import com.healing.spell.exceptions.InvalidSpellNameException;
import com.healing.spell.exceptions.NoTargetException;
import com.healing.spell.spellcast.SpellCastService;
import com.healing.spell.spellcast.request.SpellCastRequest;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("spellcasting")
public class SpellCastController {
  private Logger logger;

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
    try {
      spellCastService.castSpell(data.getSpellId(), data.getTargetId());
    } catch (NoTargetException | InvalidSpellNameException e) {
      System.err.println(e);
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body("");
  }
}
