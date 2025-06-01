package com.edutrackerz.koclukApp.converters;

import com.edutrackerz.koclukApp.dtos.ExamBriefResultDto;
import com.edutrackerz.koclukApp.dtos.ExamDetailedResultDto;
import com.edutrackerz.koclukApp.entities.ExamResult;
import com.edutrackerz.koclukApp.entities.TopicResult;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ExamResult entity’sinden DTO’lara dönüştürme işlemlerini yapar.
 */
@Component
public class ExamResultConverter {

    /**
     * ExamResult entity'sini ExamBriefResultDto'ya çevirir.
     * - Her derse ait doğru/yanlış/boş sayısını doğrudan entity’den alır.
     * - dto.setTotalNet(...), entity.getTotalNet() metodunu çağırarak tüm sınavın netini hesaplar.
     */
    public ExamBriefResultDto convertToBriefDto(ExamResult result) {
        ExamBriefResultDto dto = new ExamBriefResultDto();

        // Sınav bilgileri
        if (result.getExam() != null) {
            dto.setExamId(result.getExam().getId());
            dto.setExamName(result.getExam().getName());
            dto.setExamDate(result.getExam().getExamDate());
        }

        // Türkçe
        dto.setTurkceCorrect(result.getTurkceCorrect());
        dto.setTurkceWrong(  result.getTurkceWrong());
        dto.setTurkceEmpty(  result.getTurkceEmpty());

        // Matematik
        dto.setMatematikCorrect(result.getMatematikCorrect());
        dto.setMatematikWrong(  result.getMatematikWrong());
        dto.setMatematikEmpty(  result.getMatematikEmpty());

        // Fen
        dto.setFenCorrect(result.getFenCorrect());
        dto.setFenWrong(  result.getFenWrong());
        dto.setFenEmpty(  result.getFenEmpty());

        // Sosyal
        dto.setSosyalCorrect(result.getSosyalCorrect());
        dto.setSosyalWrong(  result.getSosyalWrong());
        dto.setSosyalEmpty(  result.getSosyalEmpty());

        // Din
        dto.setDinCorrect(result.getDinCorrect());
        dto.setDinWrong(  result.getDinWrong());
        dto.setDinEmpty(  result.getDinEmpty());

        // Yabancı Dil
        dto.setYabanciCorrect(result.getYabanciCorrect());
        dto.setYabanciWrong(  result.getYabanciWrong());
        dto.setYabanciEmpty(  result.getYabanciEmpty());

        // Toplam Net (ExamResult.getTotalNet() zaten tüm derslerin correct/wrong’dan neti toplar)
        dto.setTotalNet(result.getTotalNet());

        return dto;
    }

    /**
     * ExamResult entity'sini ExamDetailedResultDto'ya çevirir.
     * @return
     *   - Map<SubjectAdı, List<TopicWrongOnly>> yapısı:
     *       * Subject adı → o dersteki konu isimleri + her konu için yalnızca yanlış sayısı
     */
    public ExamDetailedResultDto convertToDetailedDto(ExamResult result) {
        // subject adı → [ (topicName, wrongCount), (topicName, wrongCount), … ]
        Map<String, List<ExamDetailedResultDto.TopicWrongOnly>> detailedScores = new TreeMap<>();

        if (result.getTopicResults() != null) {
            for (TopicResult tr : result.getTopicResults()) {
                if (tr.getTopic() == null || tr.getTopic().getSubject() == null) {
                    continue; // eksik ilişki varsa atla
                }
                String subjectName = tr.getTopic().getSubject().getName();
                String topicName   = tr.getTopic().getName();
                int    wrongCount  = tr.getWrongCount();

                // Yanlış sıfır bile olsa, “detaylı” görmek istediğimiz için ekliyoruz.
                // Eğer sadece yanlış>0 eklenmesini isterseniz, “if (wrongCount>0) { … }” diyebilirsiniz.
                ExamDetailedResultDto.TopicWrongOnly wrapper =
                        new ExamDetailedResultDto.TopicWrongOnly(topicName, wrongCount);

                detailedScores
                        .computeIfAbsent(subjectName, k -> new ArrayList<>())
                        .add(wrapper);
            }
        }

        ExamDetailedResultDto dto = new ExamDetailedResultDto();
        dto.setDetailedScores(detailedScores);

        // Sınavın adını ve tarihini ekleyelim:
        if (result.getExam() != null) {
            dto.setExamName(result.getExam().getName());
            dto.setExamDate(result.getExam().getExamDate());
        }

        return dto;
    }
}
