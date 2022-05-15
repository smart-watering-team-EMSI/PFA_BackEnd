package spring.cours.mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cours.mvc.model.Alert;
import spring.cours.mvc.model.Ferme;
import spring.cours.mvc.model.Parcelle;
import spring.cours.mvc.model.TempFerm;
import spring.cours.mvc.repository.AlertRepository;
import spring.cours.mvc.repository.FermeRepository;
import spring.cours.mvc.repository.ParcelleRepository;

@RestController
@RequestMapping("ferme")
public class FermeController {
	@Autowired
	private FermeRepository fermeRepository;
	@Autowired
	private AlertRepository alertRepository;
	@Autowired
	private ParcelleRepository parcelleRepository;

	@GetMapping("/all")
	public List<Ferme> findAll() {
		return fermeRepository.findAll();
	}

	@GetMapping("/alltemp")
	public List<TempFerm> findtempferm() {
		List<TempFerm> list = new ArrayList<>();
		for (Ferme ferm : fermeRepository.findAll()) {

			for (Parcelle parcelle : parcelleRepository.findAll()) {

				for (Alert alert : alertRepository.findAll()) {
					if (ferm.getId() == parcelle.getFerme().getId()
							&& alert.getParcelle().getId() == parcelle.getId()) {

						list.add(new TempFerm(ferm.getId(), alert.getTemperature(), alert.getDate()));
					}
				}
			}
		}
		return list;
	}

	@GetMapping(value = "/{id}")
	public Optional<Ferme> findByCode(@PathVariable final int id) {
		return fermeRepository.findById(id);
	}

	@PostMapping(value = "/save")
	public void save(@RequestBody final Ferme ferme) {
		fermeRepository.save(ferme);
	}

	@DeleteMapping(value = "/delete/{id}")
	public void delete(@PathVariable(required = true) int id) {
		fermeRepository.deleteById(id);
	}
}
