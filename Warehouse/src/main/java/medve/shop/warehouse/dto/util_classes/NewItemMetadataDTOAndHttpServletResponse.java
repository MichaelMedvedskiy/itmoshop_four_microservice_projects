package medve.shop.warehouse.dto.util_classes;

import medve.shop.warehouse.dto.NewItemMetadataDTO;

import javax.servlet.http.HttpServletResponse;

public class NewItemMetadataDTOAndHttpServletResponse {

    private NewItemMetadataDTO newItem;
    private HttpServletResponse httpServletResponse;

    public NewItemMetadataDTOAndHttpServletResponse(NewItemMetadataDTO newItem, HttpServletResponse httpServletResponse) {
        this.newItem = newItem;
        this.httpServletResponse = httpServletResponse;
    }

    public NewItemMetadataDTO getNewItem() {
        return newItem;
    }

    public void setNewItem(NewItemMetadataDTO newItem) {
        this.newItem = newItem;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
