/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author juan
 */
class LazyCIE10DataModel extends LazyDataModel<DiagnosticoCIE10> {

    private List<DiagnosticoCIE10> dataSource;

    public LazyCIE10DataModel(List<DiagnosticoCIE10> diagnosticos) {
        this.dataSource = diagnosticos;
    }

    @Override
    public List<DiagnosticoCIE10> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        List<DiagnosticoCIE10> data = new ArrayList<DiagnosticoCIE10>();

        //filter
        for (DiagnosticoCIE10 proc : dataSource) {
            boolean coincide = true;
            
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(
                                proc.getClass().getDeclaredMethod(
                                        "get"+filterProperty.replaceFirst(
                                                filterProperty.substring(0, 1),
                                                filterProperty.substring(0, 1).toUpperCase()
                                        )).invoke(proc));
                        
                        if (filterValue == null || fieldValue.contains(filterValue.toString())) {
                            coincide = true;
                        } else {
                            coincide = false;
                            break;
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                        coincide = false;
                    }
                }
            }

            if (coincide) {
                data.add(proc);
            }
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
