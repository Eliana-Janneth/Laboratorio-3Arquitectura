import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './semestre-academico.reducer';

export const SemestreAcademicoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const semestreAcademicoEntity = useAppSelector(state => state.semestreAcademico.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="semestreAcademicoDetailsHeading">
          <Translate contentKey="labArquiJHipsterApp.semestreAcademico.detail.title">SemestreAcademico</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.id}</dd>
          <dt>
            <span id="semestreAcademicoId">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.semestreAcademicoId">Semestre Academico Id</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.semestreAcademicoId}</dd>
          <dt>
            <span id="codigo">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.codigo}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.estado ? 'true' : 'false'}</dd>
          <dt>
            <span id="anio">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.anio">Anio</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.anio}</dd>
          <dt>
            <span id="periodoAcademico">
              <Translate contentKey="labArquiJHipsterApp.semestreAcademico.periodoAcademico">Periodo Academico</Translate>
            </span>
          </dt>
          <dd>{semestreAcademicoEntity.periodoAcademico}</dd>
        </dl>
        <Button tag={Link} to="/semestre-academico" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/semestre-academico/${semestreAcademicoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SemestreAcademicoDetail;
