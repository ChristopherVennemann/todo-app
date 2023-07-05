interface Item {
    id: number;
    message: string,
    done: boolean;
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