interface Item {
    message: string,
    id: number;
    _links: {
        collection: {
            href: string;
        }
        delete: {
            href: string;
        }
    }
}

export default Item;