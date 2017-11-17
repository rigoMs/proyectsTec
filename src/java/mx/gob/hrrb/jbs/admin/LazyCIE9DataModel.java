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
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author juan
 */
class LazyCIE9DataModel extends LazyDataModel<ProcedimientoCIE9> {
    
    private List<ProcedimientoCIE9> dataSource;

    public LazyCIE9DataModel(List<ProcedimientoCIE9> procedimientos) {
        this.dataSource = procedimientos;
    }
    
    @Override
    public List<ProcedimientoCIE9> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        List<ProcedimientoCIE9> data = new ArrayList<ProcedimientoCIE9>();

        //filter
        for (ProcedimientoCIE9 diag : dataSource) {
            boolean coincide = true;
            
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(
                                diag.getClass().getDeclaredMethod(
                                        "get"+filterProperty.replaceFirst(
                                                filterProperty.substring(0, 1),
                                                filterProperty.substring(0, 1).toUpperCase()
                                        )).invoke(diag));
                        
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
                data.add(diag);
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
