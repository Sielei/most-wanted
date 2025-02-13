package com.hs.backend.wanted;

public class WantedUtil {

    private WantedUtil() {}
    public static PagedCollection<MostWanted> mapWantedListToPagedCollection(MostWantedList mostWantedList, Integer page, Integer pageSize) {
        var totalElements = mostWantedList.total();
        var totalPages = (int) Math.ceil((double) totalElements / pageSize);
//        if (page < 1 || page > totalPages) {
//            throw new IllegalArgumentException("Page number out of bounds.");
//        }
        var startIndex = 0;
        var endIndex = Math.min(pageSize, totalElements);
        var data = mostWantedList.items().subList(startIndex, endIndex);

        return new PagedCollection<>(
                data,
                totalElements,
                page,
                totalPages,
                page == 1,
                page == totalPages,
                page < totalPages,
                page > 1
        );
    }


    public static String generateURIFromFilterParam(FilterParam filterParam) {
        var uriBuilder = new StringBuilder("/list");
        uriBuilder.append("?page=").append(filterParam.page());
        uriBuilder.append("&pageSize=").append(filterParam.pageSize());
        if (filterParam.eyeColor() != null){
            uriBuilder.append("&eyes=").append(filterParam.eyeColor().toLowerCase());
        }
        if (filterParam.name() != null){
            uriBuilder.append("&title=").append(filterParam.name().toLowerCase());
        }
        if (filterParam.hairColor() != null){
            uriBuilder.append("&hair=").append(filterParam.hairColor().toLowerCase());
        }
        if (filterParam.race() != null){
            uriBuilder.append("&race=").append(filterParam.race().toLowerCase());
        }
        if (filterParam.fieldOffice() != null){
            uriBuilder.append("&field_offices=").append(filterParam.fieldOffice().toLowerCase());
        }
        if (filterParam.sex() != null){
            uriBuilder.append("&sex=").append(filterParam.sex().toLowerCase());
        }
        if (filterParam.status() != null){
            uriBuilder.append("&status=").append(filterParam.status().toLowerCase());
        }
        return uriBuilder.toString();
    }

    public static String generateCacheKeyFromFilterParam(FilterParam filterParam) {
        var cacheKeyBuilder = new StringBuilder("most:wanted:");
        cacheKeyBuilder.append("p").append(filterParam.page());
        cacheKeyBuilder.append("ps").append(filterParam.pageSize());
        if (filterParam.eyeColor() != null){
            cacheKeyBuilder.append("e").append(filterParam.eyeColor());
        }
        if (filterParam.name() != null){
            cacheKeyBuilder.append("n").append(filterParam.name());
        }
        if (filterParam.hairColor() != null){
            cacheKeyBuilder.append("h=").append(filterParam.hairColor());
        }
        if (filterParam.race() != null){
            cacheKeyBuilder.append("r").append(filterParam.race());
        }
        if (filterParam.fieldOffice() != null){
            cacheKeyBuilder.append("f_o").append(filterParam.fieldOffice());
        }
        if (filterParam.sex() != null){
            cacheKeyBuilder.append("sx").append(filterParam.sex());
        }
        if (filterParam.status() != null){
            cacheKeyBuilder.append("s").append(filterParam.status());
        }
        return cacheKeyBuilder.toString();
    }
}
