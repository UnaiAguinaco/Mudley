package net.javaguides.springboot.service.valoration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Valoration;

import net.javaguides.springboot.repository.ValorationRepository;

@Service
public class ValorationServiceImpl implements ValorationService {

	@Autowired
	private ValorationRepository valorationRepository;

	public ValorationServiceImpl(ValorationRepository valorationRepository) {
		super();
		this.valorationRepository = valorationRepository;
	}

	@Override
	public List<Valoration> getAllValorations() {
		return valorationRepository.findAll();
	}

	@Override
	public void saveValoration(Valoration val) {
		this.valorationRepository.save(val);

	}

	@Override
	public void deleteValorationById(int id) {
		this.valorationRepository.deleteById(id);

	}

	@Override
	public Valoration getValorationById(int id) {
		List<Valoration> valorations = getAllValorations();
		for (Valoration valoration : valorations) {
			if (valoration.getId() == id) {
				return valoration;
			}
		}
		//User user = userRepository.getOne(id);
		return null;

	}

}
