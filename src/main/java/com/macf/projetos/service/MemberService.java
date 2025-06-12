package com.macf.projetos.service;


import com.macf.projetos.entity.Member;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    private final Map<Long, Member> fakeDb = new HashMap<>();

    public MemberService() {
        Member m1 = new Member();
        m1.setId(2L);
        m1.setName("Maria");
        m1.setRole("funcionário");

        Member m2 = new Member();
        m2.setId(4L);
        m2.setName("José");
        m2.setRole("funcionário");

        Member m3 = new Member();
        m3.setId(6L);
        m3.setName("Ana");
        m3.setRole("funcionário");

        // Mockando o GERENTE
        Member gerente = new Member();
        gerente.setId(5L);
        gerente.setName("Carlos Gerente");
        gerente.setRole("gerente");

        fakeDb.put(2L, m1);
        fakeDb.put(4L, m2);
        fakeDb.put(6L, m3);
        fakeDb.put(5L, gerente);
    }

    public Member findById(Long id) {
        if (!fakeDb.containsKey(id)) {
            throw new IllegalArgumentException("Membro não encontrado");
        }
        return fakeDb.get(id);
    }
}
