grammar string;

MULTILINE_STRING_LITERAL: '"""' (ESC|.)*? '"""';

STATIC_STRING_LITERAL: '"' (ESC|.)*? '"';
fragment ESC: '\\' | '\\\\';

string: MULTILINE_STRING_LITERAL
      | STATIC_STRING_LITERAL
      ;