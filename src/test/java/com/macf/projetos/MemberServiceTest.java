package com.macf.projetos;

import com.macf.projetos.entity.Member;
import com.macf.projetos.service.MemberService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Test
    void deveRetornarMembroExistente() {
        MemberService service = new MemberService();
        Member m = service.findById(1L);
        assertEquals("João", m.getName());
    }

    @Test
    void deveLancarExcecaoParaIdInexistente() {
        MemberService service = new MemberService();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.findById(99L));
        assertTrue(ex.getMessage().contains("Membro não encontrado"));
    }
}
