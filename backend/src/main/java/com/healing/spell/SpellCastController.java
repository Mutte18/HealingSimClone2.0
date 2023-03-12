package com.healing.spell;

import com.healing.exceptionhandling.exceptions.SpellNotFoundException;
import com.healing.exceptionhandling.exceptions.TargetNotFoundException;
import com.healing.spell.spellcast.GlobalCooldownHandler;
import com.healing.spell.spellcast.SpellCastService;
import com.healing.spell.spellcast.SpellCastingHandler;
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
  private final SpellCastingHandler spellCastingHandler;
  private final GlobalCooldownHandler globalCooldownHandler;

  @Autowired
  public SpellCastController(
      SpellCastService spellCastService,
      SpellCastingHandler spellCastingHandler,
      GlobalCooldownHandler globalCooldownHandler) {
    this.spellCastService = spellCastService;
    this.spellCastingHandler = spellCastingHandler;
    this.globalCooldownHandler = globalCooldownHandler;
  }

  @PostMapping(produces = "application/json")
  public ResponseEntity<String> castSpell(@RequestBody SpellCastRequest data) throws TargetNotFoundException {
    System.out.println("GGs " + data);
    if (data.getSpellId() == null || data.getTargetId() == null) {
      return ResponseEntity.status(400).body("Missing spellId or targetId");
    }
    spellCastService.castSpell(data.getSpellId(), data.getTargetId());
    return ResponseEntity.ok().body("");
  }

  @PostMapping("/cancel")
  public ResponseEntity<String> cancelSpellCast() {
    spellCastingHandler.cancelSpellCast();
    return ResponseEntity.ok().body("");
  }
}
