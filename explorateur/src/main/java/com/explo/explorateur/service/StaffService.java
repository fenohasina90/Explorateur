package com.explo.explorateur.service;

import org.springframework.stereotype.Service;

import com.explo.explorateur.repository.constant.AnneeExerciceRepository;
import com.explo.explorateur.repository.constant.RoleStaffRepository;
import com.explo.explorateur.repository.membre.InstructeurRepository;
import com.explo.explorateur.repository.membre.StaffRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.explo.explorateur.dto.StaffListeDTO;
import com.explo.explorateur.entite.membre.Staff;

@Service
public class StaffService {
    private final StaffRepository staffRepository;
    private final InstructeurRepository instructeurRepository;
    private final RoleStaffRepository rolesStaffRepository;
    private final AnneeExerciceRepository anneeExerciceRepository;
    
    @Autowired
    public StaffService(StaffRepository staffRepository, InstructeurRepository instructeurRepository, RoleStaffRepository rolesStaffRepository, AnneeExerciceRepository anneeExerciceRepository) {
        this.staffRepository = staffRepository;
        this.instructeurRepository = instructeurRepository;
        this.rolesStaffRepository = rolesStaffRepository;
        this.anneeExerciceRepository = anneeExerciceRepository;
    }
    
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public List<StaffListeDTO> getListeStaff(Integer anneeExerciceId, Integer roleId) {
        return staffRepository.findStaffListe(anneeExerciceId, roleId);
    }

    public void saveStaff(Integer idInstructeur, Integer anneeExerciceId, Integer roleId) {
        Staff staff = new Staff();
        staff.setInstructeur(instructeurRepository.findById(idInstructeur.longValue()).orElse(null));
        staff.setAnneeExercice(anneeExerciceRepository.findById(anneeExerciceId.longValue()).orElse(null));
        staff.setRole(rolesStaffRepository.findById(roleId.longValue()).orElse(null));
        staffRepository.save(staff);
    }
    
    
}
