package com.roo.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.roo.entity.dao.LedgerInfo;
import com.roo.entity.qo.LedgerQo;
import com.roo.mapper.LedgerMapper;
import com.roo.service.LedgerService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequestMapping("/ledger")
@RestController
public class LedgerController {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerService ledgerService;

    /**
     * 搜索台账
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Object> queryLedgerList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "15") int size,
            @RequestParam(name = "beginDate", required = false) String beginDate,
            @RequestParam(name = "endDate", required = false) String endDate) {
        Date beginDateParam = null;
        Date endDateParam = null;

        try {
            if (beginDate != null && !beginDate.equals(""))
                beginDateParam = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH)
                        .parse(beginDate);
            if (endDate != null && !endDate.equals(""))
                endDateParam = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH)
                        .parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("日期解析错误");
        }
        return ledgerService.queryLedgerList(page, size, beginDateParam, endDateParam);
    }

    /**
     * 创建台账
     * @param ledgerQo
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createLedgerList(@RequestBody LedgerQo ledgerQo) {
        return ledgerService.createLedgerList(ledgerQo);
    }

    /**
     * 更新台账
     * TODO
     * @param pathMatch
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateLedgerList(@RequestBody List<String> pathMatch) {
        return null;
    }

    /**
     * 根据台账ID，将台账放入回收站（逻辑删除）
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> dropLedgerList(@PathVariable Long id) {
        return ledgerService.dropLedgerList(id);
    }

    /**
     * 按时间范围获取台账Excel
     * TODO: 抽离部分代码到Service层，将Excel生成类抽离到Utils中，设置Enum枚举指定字段用于生成Excel
     * @param response
     * @param beginDate
     * @param endDate
     * @throws IOException
     */
    @GetMapping("/get-excel")
    public void getExcel(
            HttpServletResponse response,
            @RequestParam(name = "beginDate", required = false) String beginDate,
            @RequestParam(name = "endDate", required = false) String endDate) throws IOException {
        Date beginDateParam = null;
        Date endDateParam = null;

        try {
            if (beginDate != null && !beginDate.equals(""))
                beginDateParam = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH)
                        .parse(beginDate);
            if (endDate != null && !endDate.equals(""))
                endDateParam = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH)
                        .parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data_export.xlsx");

        /* 创建标题行等... =============================================================== */
        LambdaQueryWrapper<LedgerInfo> queryWrapper = new LambdaQueryWrapper<>();
        // 规则1：仅查询del_flag为2的数据
        queryWrapper.eq(LedgerInfo::getDelFlag, 2);

        // 规则2：按给定时间范围查询
        if (beginDateParam != null) {
            queryWrapper.ge(LedgerInfo::getShipmentDate, beginDateParam);
        }
        if (endDateParam != null) {
            endDateParam = DateUtils.addDays(endDateParam, 1);
            queryWrapper.le(LedgerInfo::getShipmentDate, endDateParam);
        }

        // 规则3：按最近日期在前，若日期相同，按最大ID靠前返回
        // orderByDesc已经确保了ID降序，但日期部分需要确保createTime也是降序
        queryWrapper.orderByDesc(LedgerInfo::getShipmentDate);
        queryWrapper.orderByDesc(LedgerInfo::getPkId);

        List<LedgerInfo> ledgerInfos = ledgerMapper.selectList(queryWrapper);

        // 填入sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data Export");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("商品编码");
        headerRow.createCell(1).setCellValue("商品名称");
        headerRow.createCell(2).setCellValue("单价");
        headerRow.createCell(3).setCellValue("数量");
        headerRow.createCell(4).setCellValue("总价");
        headerRow.createCell(5).setCellValue("已付");
        headerRow.createCell(6).setCellValue("待付");
        headerRow.createCell(7).setCellValue("出货人");
        headerRow.createCell(8).setCellValue("出货人手机号");
        headerRow.createCell(9).setCellValue("出货人邮箱");
        headerRow.createCell(10).setCellValue("出货日期");
        headerRow.createCell(11).setCellValue("备注");
        headerRow.createCell(12).setCellValue("台账创建时间");

        int rowNum = 1;
        for (LedgerInfo data : ledgerInfos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getProductCode());
            row.createCell(1).setCellValue(data.getProductName());
            row.createCell(2).setCellValue(data.getUnitPrice());
            row.createCell(3).setCellValue(data.getQuantityShipped());
            row.createCell(4).setCellValue(data.getTotalAmount());
            row.createCell(5).setCellValue(data.getPaidAmount());
            row.createCell(6).setCellValue(data.getBalanceAmount());
            row.createCell(7).setCellValue(data.getShipperName());
            row.createCell(8).setCellValue(data.getShipperPhone());
            row.createCell(9).setCellValue(data.getShipperEmail());
            row.createCell(10).setCellValue(data.getShipmentDate());
            row.createCell(11).setCellValue(data.getShipmentNotes());
            row.createCell(12).setCellValue(data.getGmtCreate());
        }
        /* ============================================================================= */

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
    }
}
