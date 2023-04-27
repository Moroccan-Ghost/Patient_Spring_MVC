package ma.enset.patient_spring_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.patient_spring_mvc.entities.Patient;
import ma.enset.patient_spring_mvc.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    PatientRepository patientRepository;



    @GetMapping({"/index","/"})
    /*we can use the args page && size without @RequestParam if they have the same name in the URL data, else we must specify it*/
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0") int page,
                        @RequestParam(name="size",defaultValue = "4")int size,
                        @RequestParam(name="keyword",defaultValue = "")String keyword
                        )
    {
        Page<Patient> pagePatient = patientRepository.findByNameContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listesPatients",pagePatient.getContent());
        model.addAttribute("pages",new int[pagePatient.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    /*we can use the args page && size without @RequestParam if they have the same name in the URL data, else we must specify it*/
    public String delete(@RequestParam(name="id",defaultValue = "0") Long id,
                         @RequestParam(name="keyword",defaultValue = "") String keyword,
                         @RequestParam(name="page",defaultValue = "0") int page)
    {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(name="keyword",defaultValue = "") String keyword,
                       @RequestParam(name="page",defaultValue = "0") int page){
        if(bindingResult.hasErrors()){
            return "formPatients";
        }
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model,Long id,
                              @RequestParam(name="keyword",defaultValue = "") String keyword,
                              @RequestParam(name="page",defaultValue = "0") int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("No Patient Found");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }


}
