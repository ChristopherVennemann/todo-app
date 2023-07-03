interface CollectionModel {
    _embedded: {
        itemResponseList: Item[];
    }
    _links: LinkCollection;
}

export default CollectionModel;