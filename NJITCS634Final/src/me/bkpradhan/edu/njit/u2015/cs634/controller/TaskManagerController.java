package me.bkpradhan.edu.njit.u2015.cs634.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.bkpradhan.edu.njit.u2015.cs634.domain.EvaluationResult;
import me.bkpradhan.edu.njit.u2015.cs634.domain.FileUploadResult;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionResult;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionStatistics;
import me.bkpradhan.edu.njit.u2015.cs634.domain.PredictionSummary;
import me.bkpradhan.edu.njit.u2015.cs634.service.NaiveBayesClassifierService;
import me.bkpradhan.edu.njit.u2015.cs634.util.CsvToArffConverter;
import me.bkpradhan.edu.njit.u2015.cs634.util.Helper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TaskManagerController implements HandlerExceptionResolver {

	NaiveBayesClassifierService nbClassifier = new NaiveBayesClassifierService();

	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public String doTraining(@RequestParam("force") Boolean force,
			@RequestParam("fold") Integer foldCount) {
		if (foldCount == null || foldCount < 2 || foldCount > 100 ) {
			return "Invalid fold count [ "
					+ foldCount
					+ " ]. Number of folds must be greater than 1 and less than 101. Please specify valid fold count for evaluation! e.g 5 or 10.";
		}
		EvaluationResult evr;
		try {
			evr = nbClassifier.train(foldCount, force);
			return evr.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}

	}

	@RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public void prepareTrainingSet() throws Exception {
		nbClassifier.train(10, false);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody FileUploadResult handleFileUpload(
			@RequestParam("fileType") String fileType,
			@RequestParam("file") MultipartFile file,
			@RequestParam("sessionid") String sessionid) {

		FileUploadResult result = new FileUploadResult();
		result.setRecvTime(new Date().getTime());
		result.setSessionId(sessionid);
		result.setUploadTypeFlag(fileType);
		if (file == null) {
			result.setException("Invalid file, please try again.");
			return result;
		}

		if (fileType == null) {
			result.setException("Invalid fileType in request parameter, must be one of 'testing' or 'training'. please try again.");
			return result;
		}
		boolean testing = fileType.toLowerCase().equals("testing");
		boolean training = fileType.toLowerCase().equals("training");
		if (!testing && !training) {
			result.setException("Invalid fileType: "
					+ fileType
					+ " Please try again, must be one of 'testing' or 'training'");
			return result;
		}
		boolean csvFile = file.getOriginalFilename().toLowerCase()
				.endsWith(".csv");
		boolean arffFile = file.getOriginalFilename().toLowerCase()
				.endsWith(".arff");
		if (!csvFile && !arffFile) {
			result.setException("Invalid file: "
					+ file.getOriginalFilename()
					+ " Please try again. File name must end with either .csv or .arff ( weka file)");
			return result;
		}
		sessionid = sessionid == null ? "" : sessionid.trim();
		String derivedFileName = testing ? sessionid + "_"
				+ Helper.TESTING_DATA : Helper.TRAINING_DATA;
		String savedFile = Helper.getBasePath() + "/" + derivedFileName
				+ ".csv";
		if (arffFile) {
			savedFile = Helper.getBasePath() + "/" + derivedFileName + ".arff";
		}
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(savedFile)));
				stream.write(bytes);
				stream.close();
				result.setUploadFinish(new Date().getTime());
				if (csvFile) {
					CsvToArffConverter.convert(savedFile, Helper.getBasePath()
							+ "/" + derivedFileName + ".arff");
					result.setArffFinish(new Date().getTime());
				}
				if (training) {
					nbClassifier.setTrainingFileDirty(false);
				}
				result.setFileSizeBytes(bytes.length);
				result.setFilename(file.getOriginalFilename());
				result.setMessage("The file was successfully uploaded");
				return result;
			} catch (Exception e) {
				result.setMessage("The file upload failed!");
				result.setException(e.getMessage());
				return result;
			}
		} else {
			result.setMessage("You are tried to upload an empty file! Please try again.");
			return result;
		}
	}

	@RequestMapping(value = "/predict", method = RequestMethod.GET, headers = "Accept=application/json")
	public PredictionSummary getClassificationResult(
			@RequestParam("sessionid") String sessionid) {
		sessionid = sessionid == null ? "" : sessionid.trim();
		try {
			return nbClassifier.predictNow(Helper.getBasePath() + "/"
					+ sessionid + "_testingdata.arff");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return errorResult("getClassificationResult:/analyze", e);
		}

	}

	protected PredictionSummary errorResult(String identifier, Exception e) {
		PredictionResult res = new PredictionResult();
		res.setCid(0);
		res.setRecord(identifier);
		res.setPrediction(e.getMessage());
		res.setMatch("ERROR");
		// res.setProbDistribution(e.getMessage());
		List<PredictionResult> error = new ArrayList<PredictionResult>(1);
		error.add(res);
		PredictionSummary ps = new PredictionSummary();
		ps.setResults(error);
		PredictionStatistics pStat = new PredictionStatistics();
		pStat.setName(res.getMatch());
		pStat.setTotalInstances(1);
		pStat.setTotalMatchedOk(0);
		ps.setStat(pStat);
		return ps;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		System.out.println(arg3.getMessage());
		return null;
	}

}