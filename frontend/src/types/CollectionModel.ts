import Item from "@/types/Item";
import LinkCollection from "@/types/LinkCollection";

interface CollectionModel {
    _embedded: {
        itemResponseList: Item[];
    }
    _links: LinkCollection;
}

export default CollectionModel;