package com.newcarshop.springbatch.validate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.newcarshop.springbatch.converter.CarroConverter;
import com.newcarshop.springbatch.dto.CarroDto;
import com.newcarshop.springbatch.utils.CsvFileUtils;

public class CarroValidate {

	public CarroValidate() {
	}

	public static List<CarroDto> validate(List<CarroDto> carroDtoList) throws IOException {

		List<CarroDto> validList = new ArrayList<>();
		List<CarroDto> invalidList = new ArrayList<>();

		carroDtoList.stream().forEach(dto -> {

			if (isValid(dto)) {
				validList.add(dto);
			} else {
				invalidList.add(dto);
			}
		});

		createCsvToInvalidList(invalidList);
		return validList;
	}

	private static void createCsvToInvalidList(List<CarroDto> invalidList) throws IOException {

		if (!invalidList.isEmpty()) {

			CsvFileUtils csvOutInvalid = new CsvFileUtils("invalid-import", false);

			invalidList.stream().forEach(carro -> {
				try {
					csvOutInvalid.writerError(CarroConverter.carroToStringArray(carro));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			csvOutInvalid.closeWriter();
		}
	}

	public void emptyFile(LocalDateTime localDateTime) throws IOException {

		CsvFileUtils emptyCSV = new CsvFileUtils("empty-file", false);

		try {
			emptyCSV.writerError(CarroConverter.getErrorToStringArray("Tentativa de Import de arquivo vazio!"));
			emptyCSV.closeWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isValid(CarroDto carroDto) {
		return StringUtils.isNotBlank(carroDto.getRenavam()) && StringUtils.isNotBlank(carroDto.getMarca())
				&& StringUtils.isNotBlank(carroDto.getModelo()) && StringUtils.isNotBlank(carroDto.getAnoFabricacao())
				&& StringUtils.isNotBlank(carroDto.getModelo()) && StringUtils.isNotBlank(carroDto.getValorCompra());
	}
}
